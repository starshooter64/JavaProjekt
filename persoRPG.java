import java.util.*;

public class PersoRPG 
{
	private int vie,mana,degats;
	private String nom;
	private Object inventaire[];  
	public PersoRPG()
	{
		vie = 0
		mana = 0;
		degats = 0;
		nom = " ";
	}
	public PersoRPG(int a,int b,int c, String d)
	{
		vie = a;
		mana = b;
		degats = c;
		nom = d;
	}
	// accesseurs 
	public void setVie(int a)
	{
		vie = a;
	}
	public void setMana(int a)
	{
		mana = a;
	}
	public void setDegats(int a)	
	{
		degats = a;
	}
	public void setNom(String a)
	{
		nom = a;
	}
	public int getVie()
	{
		return vie;
	}
	public int getMana()
	{
		return mana;
	}
	public int getDegats()
	{
		return degats;
	}
	public String getNom()
	{
		return nom;
	}
	public attaquer(persoRPG a )
	{
		System.out.println(this.nom+" attaque " + a.nom); // j'ai mis un perso en cible mais ca peut etre autre chose faut juste qu'on voit quoi et comment faire
		a.vie -= this.degats;
			
	}
	public attaquer(Monstre a)
	{
		System.out.println(this.nom+" attaque " + a.nom); // j'ai mis un perso en cible mais ca peut etre autre chose faut juste qu'on voit quoi et comment faire
		a.vie -= this.degats;	
	}
	public Object ListeInventaire()
	{
		for(Object obj : inventaire)
		{
			System.out.println(obj);
		}
	}
}