import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class Catalog implements Iterable<Crop> {
	Map<String, Crop> crops;
	
	public Catalog(Iterable<Crop> starter){
		crops = new HashMap<String, Crop>();
		if(starter != null){
			this.addAll(starter);
		}
	}

	public Catalog(){
		crops = new HashMap<String, Crop>();
	}

	public boolean addAll(Iterable<Crop> toAdd){
		if(toAdd == null){
			throw new IllegalArgumentException("cannot add from a null iterable");
		}
		for(Crop c : toAdd){
			if(c == null){
				throw new IllegalArgumentException("cannot add a null crop");
			}
			crops.put(c.name(), c);
		}
		return true;
	}

	public boolean add(Crop crop){
		crops.put(crop.name(), crop);
		return true;
	}

	public boolean remove(Crop crop){
		return this.remove(crop.name());
	}

	public boolean remove(String name){
		if(!crops.containsKey(name)){
			return false;
		}
		crops.remove(name);
		return true;
	}

	public Iterator<Crop> iterator(){
		Set<Crop> res = new HashSet<Crop>();
		for(String name : crops.keySet()){
			res.add(crops.get(name));
		}
		return res.iterator();
	}
}
