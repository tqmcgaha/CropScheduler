import java.util.Arrays;

public class CropField{
	private int nights;
	private int[] spots;

	public CropField(int capacity){
		this.spots = new int[capacity];
		for(int i = 0; i < capacity; i++){
			spots[i] = 27;
		}
		this.nights = 27*capacity;
	}

	// retuns how many nights lay completely within the month
	public int nights(){
		return nights;
	}

	public int maxNights(){
		return spots[spots.length-1];
	}

	public boolean canHarvest(Crop crop){
		int max = this.maxNights();
		return crop.totalLifetime() < max;
	}

	public int plant(Crop crop){
		if(crop == null){
			return -1;
		} else {
			for(int i = 0; i < spots.length; i++){
				if(crop.totalLifetime() <= spots[i]){
					int old = spots[i];
					spots[i] -= crop.totalLifetime();
					Arrays.sort(spots);
					return 27-old;
				}
			}
			return -1;
		}
	}
}
