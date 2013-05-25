package blake2;

import java.util.Arrays;

public class BLAKE2_Hasher {

	private byte[] messageBlock;
	private byte[][] splitBlock;
	private byte[] currentBlock = new byte[128];
	
	private int messageBlockLength;
	private int splitArrayLength;
	
	public BLAKE2_Hasher(String input) throws Exception {
		this.messageBlock = input.getBytes();
		messageBlockLength = messageBlock.length;
		
		if (messageBlock.length == 128) {
			splitArrayLength = 1;
		}
		
		if (messageBlock.length < 128) {
			PadBytes(messageBlock);
			splitArrayLength = 1;
		}
		
		if (messageBlock.length > 128) {
			splitArrayLength = CalculateSplitArrayLength(messageBlock);
			
			for (int i = 0; i < splitArrayLength; i++) {
				
				for (int j = 0; j < 128; j++) {
					
					
				}
				
			}
			
		}
		
	}
	
	public BLAKE2_Hasher(byte[] input) {
		this.messageBlock = input;
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
