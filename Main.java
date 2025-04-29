public class Main {
    public static void main (String[]args){
        Dict.Puffball.studied = true;
        Notebook.entry(Dict.Puffball);
    }
}
class InventorySlot{
    Species mush;
    int count;
    InventorySlot(Species sp){
        mush = sp;
        count = 1;
    }
}

class Notebook{
    static void entry(Species sp){
        if(sp.studied){
            System.out.println(sp.name + ": " + sp.description);
            if(sp.deadly) System.out.println("Deadly to consume");
            else System.out.println("Safe to consume");
        }
        else{
            System.out.println("???: ????? ??? ????? ?? ??? ? ??????.");
            System.out.println("???????????????");
        }
    }
}

class Species{
    String name;
    String description;
    boolean deadly;
    boolean studied;
    
    Species(String n, String desc, boolean d){
        name = n;
        description = desc;
        deadly = d;
    }
}

class Dict{
    static Species Puffball = new Species("PUFFBALL","its body bursts on impact, releasing a cloud of spores",false);
    static Species Chanterelle = new Species("CHANTERELLE","often found in well-drained coniferous woodland",false);
    static Species FalsePuffball = new Species("FALSE PUFFBALL","cream-colored mass, a species of slime mold",true);
}
