package ku.cs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class LawyerList {
    private ArrayList<Lawyer> lawyers;

    public LawyerList(){
        lawyers = new ArrayList<>();
    }
    public void addLawyer(Lawyer lawyer){
        lawyers.add(lawyer);
    }

    public void sort(Comparator<Lawyer> userComparator) {
        Collections.sort(lawyers, userComparator);
    }
    public ArrayList<Lawyer> getLawyers(){
        return this.lawyers;
    }

    public boolean searchByUsername(String username){
        for(Lawyer lawyer: lawyers){
            if (lawyer.getUsernameLawyer().equals(username)) {
                // ถ้าซ้ากับใน list
                return true;
            }
        }
        return false;
    }
    public String toCsv() {
        String result = "";
        for (Lawyer lawyer : this.lawyers){
            result += lawyer.toCsv() + "\n";
        }
        return result;
    }
    @Override
    public String toString() {
        return "LawyerList{" +
                "lawyer=" + lawyers +
                '}';
    }
}
