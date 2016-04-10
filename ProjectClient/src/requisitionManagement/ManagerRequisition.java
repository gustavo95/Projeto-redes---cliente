package requisitionManagement;

import java.util.ArrayList;

public class ManagerRequisition {

	private ArrayList<Requisition> l;
	
	public ManagerRequisition(ArrayList<Requisition> l) {
		super();
		this.l = l;
	}

	public ArrayList<ArrayList<Requisition>> separateList  (String name){
		
		ArrayList<ArrayList<Requisition>> arrays  = new ArrayList<>();
		ArrayList<Requisition> myReqs = new ArrayList<Requisition>();
		ArrayList<Requisition> otherReqs = new ArrayList<Requisition>();
		for (Requisition i : l){
			if (i.getName_client().equals(name)){
				//System.out.println("myname" + i.getName_client());
				//System.out.println(myReqs.size());
				myReqs.add(i);
			}
			if (!i.getName_client().equals(name)){
				//System.out.println("othername" + i.getName_client());
				otherReqs.add(i);
			}

		}
		arrays.add(myReqs);
		arrays.add(otherReqs);
		
		
		
	
		//arrays[1]=otherReqs;
		return arrays;
	}
	public void print (String nome){
		
		System.out.println(nome);
		ArrayList<Requisition> my =separateList(nome).get(0);
		ArrayList<Requisition> others = separateList(nome).get(1);
		System.out.println("MINHAS REQUISIÇÕES: ");
		if (!my.equals(null)){
			for (Requisition i :my){

				System.out.println("Nome: "+i.getName_client()+" Nome do documento: " + i.getName_document());

			}
		}
		System.out.println("REQUISIÇÕES DE OUTROS: ");
		if (!others.equals(null)){
			for (Requisition i :others){

				System.out.println("Nome: "+i.getName_client()+" Nome do documento: " + i.getName_document()+"\n");

			}
		}
	}
}
