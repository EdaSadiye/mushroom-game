import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<InventorySlot> Inventory = new ArrayList<>();
        int AmntActions = 5;
        boolean dead = false;
        int score = -1;
        String answer;
        
        ArrayList<Species>dict = new ArrayList<>();
        dict.add(Dict.Puffball);
        dict.add(Dict.Chanterelle);
        dict.add(Dict.FalsePuffball);
        
        while(!dead){
            AmntActions = 5;
            while (AmntActions > 0){
                System.out.println("You have " + AmntActions + " actions left today.");
                System.out.println("What would you like to do?");
                System.out.println();
                answer = scanner.next();
                System.out.println();
                
                if(answer.equals("entry")){
                    Inventory.get(Inventory.size()-1).mush.studied = true;
                    Notebook.entry(Inventory.get(Inventory.size()-1).mush);
                }
                else if(answer.equals("forage")){
                    Inventory.add(new InventorySlot(dict.get((int)(Math.random()*3))));
                }
                else if(answer.equals("consume")){
                    if(Inventory.size() == 0){
                        System.out.println("You don't have food");
                    }
                    else if(Inventory.get(Inventory.size()-1).mush.deadly == true){
                        System.out.println("You died");
                        dead = true;
                        AmntActions = 0;
                    }
                    else{
                        Inventory.remove(Inventory.size()-1);
                    }
                }
                else{
                    System.out.println("Error. I didn't catch that.");
                    System.out.println();
                    continue;
                }
                System.out.println();
                AmntActions--;
            }
            score++;
        }
        System.out.println("score: " + score);
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
