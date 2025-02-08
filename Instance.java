import Jeu.*;

public class Instance {
    

    public BaseAsc baseasc1;
    public BaseAsc baseasc2;
    public BaseDesc basedesc1;
    public BaseDesc basedesc2;
    public Main main;
    public Pioche pioche;


    public Instance(BaseAsc ba1, BaseAsc ba2, BaseDesc bd1, BaseDesc bd2, Main m, Pioche p){
        baseasc1 =  ba1 == null ? null : new BaseAsc(ba1);
        baseasc2 = ba2 == null? null : new BaseAsc(ba2);
        basedesc1 = bd1 == null? null : new BaseDesc(bd1);
        basedesc2 = bd2 == null ? null : new BaseDesc(bd2);
        main = new Main(m);
        pioche = new Pioche(p);
    }

}
