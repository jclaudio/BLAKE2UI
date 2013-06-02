package blake2;

// Written by Jonathan Claudio and Craig Strange
//
//
//
//
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BLAKE2_Hasher {

	private byte[] messageBlock;
	private ArrayList<byte[]> splitBlock = new ArrayList<byte[]>();
	private byte[] currentBlock = new byte[128];
	
	private int messageBlockLength = 0;
	private int splitArrayLength = 0;
	
	public BLAKE2_Hasher(byte[] input) throws Exception {
		this.messageBlock = input;
		messageBlockLength = messageBlock.length;
		
		// If byte stream is exactly 128 bytes long
		if (messageBlockLength == 128) {
			splitArrayLength = 1;
			currentBlock = messageBlock;
		}
		
		// If byte stream is less than 128 bytes
		if (messageBlockLength < 128) {
			splitArrayLength = 1;
			currentBlock = PadBytes(messageBlock);
		}
		
		// If byte stream is longer than 128 bytes - INCOMPLETE
		if (messageBlockLength > 128) {	
			splitArrayLength = CalculateSplitArrayLength(messageBlock);
		
			// If message block is multiple of 128.  Which it probably won't be.
			if (messageBlockLength % 128 == 0) {
	
				for (int i = 0; i < splitArrayLength; i++) {
					int offset = 0;
					byte[] buffer = new byte[128];
					
					offset = i * 128;
					
					try {
						System.arraycopy(messageBlock, offset, buffer, 0, 128);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					splitBlock.add(buffer);
				}
			// If message block is not a multiple of 128
			} else {
				for (int i = 0; i < splitArrayLength - 1; i++) {
					int offset = 0;
					byte[] buffer = new byte[128];
					
					offset = i * 128;
					
					System.arraycopy(messageBlock, offset, buffer, 0, 128);
					
					splitBlock.add(buffer);
				}
				
				int trailerLength = (messageBlockLength - ((splitArrayLength - 1) * 128));
				int offset = (splitArrayLength - 1) * 128;
				byte[] buffer2 = new byte[128];
				buffer2 = PadBytes(buffer2);
				
				System.arraycopy(messageBlock, offset, buffer2, 0, trailerLength);
			}
		}	
	}
	
	public String bytesToHex(byte[] bytes) {
	    final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public byte[] CalculateHash() {
		BLAKE2b_Algorithm blake2b = new BLAKE2b_Algorithm();
		blake2b.Init();
		byte[] output = new byte[64];
		
		if (messageBlockLength > 128) {
			for (int i = 0; i < splitBlock.size(); i++) {
				byte[] currentBlock;
				
				currentBlock = splitBlock.get(i);
				blake2b.Compress(currentBlock);
			}
		} else {
			blake2b.Compress(currentBlock);
		}
		
		output = blake2b.getHash();
		return output;
	}
	
	private byte[] PadBytes(byte[] input) throws Exception {
		byte[] output = new byte[128];
		
		// fill array with zeros
		for (int i = 0; i < output.length; i++) {
			output[i] = 0x00;
		}
		
		// copy input elements into output array
		for (int i = 0; i < input.length; i++) {
			output[i] = input[i];
		}
		
		// Exception handling
		if (output.length != 128) {
			throw new Exception("Output array is of incorrect length");
		}
		
		return output;
	}
	
	private int CalculateSplitArrayLength(byte[] input) {
		int arrayLength = input.length;
		int splitArrayLength = (arrayLength / 128) + 1;
		return splitArrayLength;
		
	}
}
