package modelo;

import java.sql.SQLException;
import java.util.Scanner;

public class Entrenador {

	public static final int CAJA = 0;
	public static final int EQUIPO = 1;

	private Pokemon equipo[];
	private Caja caja;
	private String nombre;
	private int pokedollar;

	public Entrenador() {
		super();
		equipo = new Pokemon[4];
		caja = new Caja();
		nombre = "";
		pokedollar = 0;
	}

	public Entrenador(Pokemon[] equipo, Caja caja, String nombre, int pokedollar) {
		super();
		this.equipo = equipo;
		this.caja = caja;
		this.nombre = nombre;
		this.pokedollar = pokedollar;
	}

	public Pokemon[] getEquipo() {
		return equipo;
	}

	public void setEquipo(Pokemon[] equipo) {
		this.equipo = equipo;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPokedollar() {
		return pokedollar;
	}

	public void setPokedollar(int pokedollar) {
		this.pokedollar = pokedollar;
	}

	public void moverEquipoCaja() {

		Scanner sc = new Scanner(System.in);

		int cont = 0;
		int miembro;
		for (int i = 0; i < equipo.length; i++) {
			if (equipo[i] != null) {
				cont++;
			}
		}
		if (cont <= 1) {
			System.out.println("El equipo no puede estar vacio.");
		} else {
			do {
				System.out.println("¿Qué pokemon deseas guardar(1-4)?");
				miembro = sc.nextInt();
				miembro--;

			} while (equipo[miembro] == null);

			caja.getListPokemon().add(equipo[miembro]);
			equipo[miembro] = null;
		}

		sc.close();

	}

	public void moverCajaEquipo() {

		Scanner sc = new Scanner(System.in);

		int cont = 0;
		int candidato;
		Pokemon pokemon;

		if (caja.getListPokemon().isEmpty()) {
			System.out.println("La caja está vacia");
		} else {

			for (int i = 0; i < equipo.length; i++) {
				if (equipo[i] != null) {
					cont++;
				}
			}
			if (cont == 4) {
				System.out.println("El esquipo está lleno.");
			} else {

				System.out.println("Elige el pokemon que deseas unir al equipo.");
				System.out.println(caja.toString());// Te debe dar el mote
				candidato = sc.nextInt();

				pokemon = caja.getListPokemon().get(candidato);
				caja.getListPokemon().remove(candidato);

				for (int j = 0; j < equipo.length; j++) {
					cont = 0;
					if (equipo[j] == null) {
						equipo[j] = pokemon;
						cont = 1;
					}
					if (cont == 1) {
						j = 10;
					}
				}
			}
		}

		sc.close();
	}

	public void captura() {

		Pokemon pokemonCaptura = new Pokemon();
		int captura;
		int cont = 0;

		pokemonCaptura = Pokemon.generarPokemon();

		System.out.println("A ver si cae la breva.");

		captura = ((int) Math.floor(Math.random() * (1 - 3 + 1) + 3));

		if (captura > 1) {


			for (int i = 0; i < equipo.length; i++) {
				if (equipo[i] != null) {
					cont++;
				}

				if (cont < 4) {
					for (int j = 0; j < equipo.length; j++) {
						cont = 0;
						if (equipo[j] == null) {
							equipo[j] = pokemonCaptura;
							cont = 1;
						}
						if (cont == 1) {
							j = 10;
						}	
					}	try {
						DbConexion.insertarPokemon(pokemonCaptura,EQUIPO);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {

					caja.getListPokemon().add(pokemonCaptura);
					try {
						DbConexion.insertarPokemon(pokemonCaptura,CAJA);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			System.out.println("Unlucky.");
		}
	}

}
