import java.util.*; 

public class Guerrier extends persoRPG
{
	public Guerrier()
	{
		super();

	}
	public Guerrier(int a,int b, int c, String d)
	{
		super(a,b,c,d);
	}

	public  void SwordSlash(persoRPG a)
	{
		System.out.println(this.nom+" SwordSlash on  " + a.nom);
		a.vie -= this.degats + 15;
	}
	public void BattleCry()
	{
		vie += 10; 
	}
}