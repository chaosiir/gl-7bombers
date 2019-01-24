public class TestMap {
    private Map M;
    private Personnage P;
    private Bombe B;
    private Case[][] C;

    @BeforeAll
    public void creerMap(){
        try{
            M=new Map();
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void Remplissage(){
        try{
            M=M.generatePve(20,20,10);
            C=M.getGrille();
        }catch (Exception e){
            fail();
        }
    }
}
