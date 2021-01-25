package org.golde.discordbot.teentitans.brotherhood.minecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MinecraftPing {

	private static final Gson GSON = new Gson();
	private static final byte PACKET_HANDSHAKE = 0x00,
			PACKET_STATUSREQUEST = 0x00,
			PACKET_PING = 0x01;
	private static final int PROTOCOL_VERSION = 47;
	private static final int STATUS_HANDSHAKE = 1;
	
	private static final String SRV_QUERY_PREFIX = "_minecraft._tcp.%s";

	public static PingResponse getPing(final PingOptions options) throws IOException {
		
		check(options.getHostname() == null, "Hostname can't be null");

		String hostname = options.getHostname();
		int port = options.getPort();

		 try {

	            Record[] records = new Lookup(String.format(SRV_QUERY_PREFIX, hostname), Type.SRV).run();

	            if (records != null) {

	                for (Record record : records) {
	                    SRVRecord srv = (SRVRecord) record;

	                    hostname = srv.getTarget().toString().replaceFirst("\\.$", "");
	                    port = srv.getPort();
	                }

	            }
	        } catch (TextParseException e) {
	            e.printStackTrace();
	        }
		
		String json;
		long ping = -1;

		try (final Socket socket = new Socket()) {

			long start = System.currentTimeMillis();
			socket.connect(new InetSocketAddress(hostname, port), options.getTimeout());
			ping = System.currentTimeMillis() - start;

			try (DataInputStream in = new DataInputStream(socket.getInputStream());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					//> Handshake
					ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
					DataOutputStream handshake = new DataOutputStream(handshake_bytes)) {

				handshake.writeByte(PACKET_HANDSHAKE);
				writeVarInt(handshake, PROTOCOL_VERSION);
				writeVarInt(handshake, options.getHostname().length());
				handshake.writeBytes(options.getHostname());
				handshake.writeShort(options.getPort());
				writeVarInt(handshake, STATUS_HANDSHAKE);

				writeVarInt(out, handshake_bytes.size());
				out.write(handshake_bytes.toByteArray());

				//Send Status request
				out.writeByte(0x01); //Size of packet
				out.writeByte(PACKET_STATUSREQUEST);

				//Receive Status response
				readVarInt(in); //Size
				int id = readVarInt(in);

				check(id == -1, "Server prematurely ended stream.");
				check(id != PACKET_STATUSREQUEST, "Server returned invalid packet.");

				int length = readVarInt(in);
				check(length == -1, "Server prematurely ended stream.");
				check(length == 0, "Server returned unexpected value.");

				byte[] data = new byte[length];
				in.readFully(data);
				json = new String(data, "UTF-8");

				//Ping
				out.writeByte(0x09); // Size of packet
				out.writeByte(PACKET_PING);
				out.writeLong(System.currentTimeMillis());

				//Pong
				readVarInt(in); // Size
				id = readVarInt(in);
				check(id == -1, "Server prematurely ended stream.");
				check(id != PACKET_PING, "Server returned invalid packet.");

			}

		}

		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

		PingResponse output = GSON.fromJson(jsonObject, PingResponse.class);
		output.setPing(ping);

		return output;
	}

	private static int readVarInt(DataInputStream in) throws IOException {
		int i = 0;
		int j = 0;
		while (true) {
			int k = in.readByte();

			i |= (k & 0x7F) << j++ * 7;

			if (j > 5) {
				throw new RuntimeException("VarInt too big");
			}

			if ((k & 0x80) != 128) {
				break;
			}
		}

		return i;
	}

	private static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
		while (true) {
			if ((paramInt & 0xFFFFFF80) == 0) {
				out.writeByte(paramInt);
				return;
			}

			out.writeByte(paramInt & 0x7F | 0x80);
			paramInt >>>= 7;
		}
	}

	private static void check(final boolean b, final String m) throws IOException {
		if (b) {
			throw new IOException(m);
		}
	}


}
