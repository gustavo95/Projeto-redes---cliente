package connection;

import java.util.ArrayList;

public class ManagerRequisition {

	ArrayList<Requisition> l;

	public ManagerRequisition(ArrayList<Requisition> l) {
		super();
		this.l = l;
	}

	public ArrayList<Requisition>[] separateList  (String name, ArrayList<Requisition> l){
		ArrayList<Requisition>[] array =null ;
		ArrayList<Requisition> myReqs = null;
		ArrayList<Requisition> otherReqs = null;
		for (Requisition i : l){
			if (i.getName_client().equals(name)){
				myReqs.add(i);
			}
			if (!i.getName_client().equals(name)){
				otherReqs.add(i);
			}

		}
		
		array[0]=myReqs;
		array[1]=otherReqs;
		return array;
	}

}
