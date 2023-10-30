package ku.cs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LawsuitsInformationList {

    private static ArrayList<LawsuitsInformation> lawsuitsInformations;

    public LawsuitsInformationList(){ lawsuitsInformations = new ArrayList<>();}

    public static void addLawsuitsInformation(LawsuitsInformation lawsuitsInformation){lawsuitsInformations.add(lawsuitsInformation);}

    public void sort(Comparator<LawsuitsInformation> lawsuitsInformationComparator) {
        Collections.sort(lawsuitsInformations, lawsuitsInformationComparator);

    }

    public ArrayList<LawsuitsInformation> getLawsuitsInformations(){
        return this.lawsuitsInformations;
    }

    public boolean searchByLawsuitsInformation(String name){
        for(LawsuitsInformation lawsuitsInformation: lawsuitsInformations){
            if (lawsuitsInformation.getName().equals(name)) {
                // ถ้าซ้ากับใน list
                return true;
            }
        }
        return false;
    }
    public String toCsv() {
        String result = "";
        for (LawsuitsInformation lawsuitsInformation : this.lawsuitsInformations){
            result += lawsuitsInformation.toCsv() + "\n";
        }
        return result;
    }

    @Override
    public String toString() {
        return "LawsuitsInformationList{" +
                "lawsuitsInformations=" + lawsuitsInformations +
                '}';
    }
}
