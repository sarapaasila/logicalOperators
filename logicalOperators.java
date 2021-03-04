import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.util.Scanner;
import java.util.*;

public class logicalOperators {
	// Lue tiedosto annetun tiedostonimen perusteella ja kopioi sen sisältö (rivit) 
	// linkitettyyn listaan. Palauta linkitetyn listan.
	private LinkedList<String> readInput(String fileName) {
		// Luo tyhjän linkitetyn listan.
		LinkedList<String> values = new LinkedList<String>();
		String line;
		try {
			// Luo lukijaobjekti.
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			// Lisää linkitettyyn listaan tiedoston rivejä yksitellen, kunnes
			// luettavia rivejä ei enää ole.
			while((line=br.readLine())!=null) {
				values.add(line);
			}
			
			// Sulje lukijaobjekti.
			fr.close();
		} 
		catch(IOException e) {
			System.out.println("File not found.");;
		}
		// Palauta linkitetty lista.
		return(values);
	}
	
	// Lue käyttäjän syöte ja muokkaa sen perusteella linkitettyä listaa.
	// Palauta linkitetyn listan.
	private LinkedList<String> deleteValue(LinkedList<String> valuesA) {
		// Tiedustele käyttäjältä, haluaako hän poistaa yhden alkion.
		System.out.println("Would you like to delete a value from the table A?");
		System.out.println("Write a Y for yes and a N for no.");
		
		boolean incorrAnswer = true;
		do {
			// Luo muuttuja, joka lukee käyttäjän syötteen.
			Scanner input = new Scanner(System.in);
			String answer = input.nextLine();
			// Annettu vastaus oikeellinen, käyttäjä haluaa poistaa alkion.
			if (answer.equals("Y")) {
				// Poista alkio linkitetyn listan päästä.
				valuesA.remove();
				// Informoi käyttäjää operaation tilasta.
				System.out.println("A value has been removed from the table A.");
				incorrAnswer = false;
			}
			else {
				// Annettu vastaus oikeellinen, käyttäjä ei halua poistaa alkiota.
				if (answer.equals("N")) {
					incorrAnswer = false;
				}
				// Annettu vastaus ei ole oikeellinen.
				else {
					System.out.println("Please answer with a Y or a N.");
				}
			}
		}
		while (incorrAnswer == true);
		
		// Palauta (muokattu) linkitetty lista takaisin.
		return(valuesA);
	}
	
	// Luo unioni kahden annetun linkitetyn listan välille ja laske,
	// kuinka monta kertaa unionin alkiot esiintyvät annetuissa listoissa.
	private void union(LinkedList<String> valuesA, LinkedList<String> valuesB) {
		// Luo tyhjä linkitetty lista, johon sijoitetaan väliaikaisesti
		// kaikki listojen valuesA ja valuesB alkiot.
		LinkedList<String> temporalList = new LinkedList<String>();
		// Luo tyhjä linkitetty lista, joka laskee alustavasti, kuinka
		// monta kertaa alkio esiintyy listassa temporalList.
		LinkedList<String> temporalOccList = new LinkedList<String>();
		// Luo tyhjä linkitetty lista, johon sijoitetaan listan
		// temporalList alkiot ilman duplikaatteja.
		LinkedList<String> unionList = new LinkedList<String>();
		// Luo tyhjä linkitetty lista, johon sijoitetaan listan
		// temporalOccList alkiot suhteessa listaan unionList.
		LinkedList<String> occuranceList = new LinkedList<String>();
		
		// Sijoita kaikki listan valuesA alkiot listaan temporalList.
		for (int i=0; i<valuesA.size(); i++) {
			temporalList.add(valuesA.get(i));
		}
		// Sijoita kaikki listan valuesB alkiot listaan temporalList.
		for (int i=0; i<valuesB.size(); i++) {
			temporalList.add(valuesB.get(i));
		}
		
		// Laske for-luupilla, kuinka monta kertaa kukin alkio esiintyy
		// listassa temporalList.
		for	(int i=0; i<temporalList.size(); i++) {
			// Alusta muuttuja alkion ilmentymien laskemiseen.
			int occurances = 0;
			// Vertaa kahta samaa linkitettyä listaa toisiinsa.
			for (int j=0; j<temporalList.size(); j++) {
				// Jos vertailtavat alkiot ovat samoja, lisää ilmentymien 
				// laskuria yhdellä (1).
				if ((temporalList.get(i)).equals(temporalList.get(j))) {
					occurances++;
				}
			}
			// Sijoita ilmentymien määrä merkkijonona listaan temporalOccList.
			temporalOccList.add(Integer.toString(occurances));
		}
		
		// Sijoita lopullisiin listoihin unionList ja occuranceList niitä
		// vastaavien väliaikaisten listojen ensimmäiset alkiot.
		unionList.add(temporalList.getFirst());
		occuranceList.add(temporalOccList.getFirst());
		// Kopioi alkiot ilman duplikaatteja unionListiin ja occuranceListiin.
		for (int i=1;i<temporalList.size(); i++) {
			// Jos unionList ei sisällä temporalLististä saatua alkiota,
			// se lisätään unionListiin ja sitä vastaava ilmentymänumero
			// sijoitetaan occuranceListiin.
			if(!(unionList.contains(temporalList.get(i)))) {
				unionList.add(temporalList.get(i));
				occuranceList.add(temporalOccList.get(i));
			}
		}
		
		// Lähetä linkitetyt listat ja tiedoston nimi funktiolle, joka tekee
		// näistä tiedoston.
		writeOutput(unionList, occuranceList, "or.txt");
	}
	
	// Luo leikkaus kahden annetun linkitetyn listan välille ja pidä kirjaa
	// siitä, millä listan valuesA riveillä alkiot on bongattu ensimmäistä kertaa.
	private void intersection(LinkedList<String> valuesA, LinkedList<String> valuesB) {
		// Luo tyhjä linkitetty lista, johon sijoitetaan väliaikaisesti
		// kaikki alkiot, jotka esiintyvät sekä listassa valuesA että valuesB.
		LinkedList<String> temporalList = new LinkedList<String>();
		// Luo tyhjä linkitetty lista, johon sijoitetaan väliaikaisesti
		// listan temporalList alkioiden ilmentymärivit.
		LinkedList<String> temporalFileList = new LinkedList<String>();
		// Luo tyhjä linkitetty lista, johon sijoitetaan listan temporalList
		// alkiot ilman duplikaatteja.
		LinkedList<String> intersectionList = new LinkedList<String>();
		// Luo tyhjä linkitetty lista, johon sijoitetaan listan
		// temporalFileList alkiot suhteessa listaan intersectionList.
		LinkedList<String> rowList = new LinkedList<String>();
		
		// Sijoita for-luupilla väliaikaisiin listoihin alkiot ja niiden ilmentymärivit.
		for (int i=0; i<valuesA.size(); i++) {
			String x = valuesA.get(i);
			for (int j=0; j<valuesB.size(); j++) {
				// Jos alkio löytyy molemmista listoista valuesA ja valuesB,
				// lisää alkio temporalListiin ja lisätään sen ilmentymärivi
				// temporalFileListiin.
				if (x.equals(valuesB.get(j))) {
					temporalList.add(x);
					temporalFileList.add(Integer.toString(i));
				}
			}
		}
		
		// Sijoita lopullisiin listoihin intersectionList ja rowList niitä
		// vastaavien väliaikaisten listojen ensimmäiset alkiot.
		intersectionList.add(temporalList.getFirst());
		rowList.add(temporalFileList.getFirst());
		// Kopioi alkiot ilman duplikaatteja intersectionListiin ja rowListiin.
		for (int i=1;i<temporalList.size(); i++) {
			// Jos intersectionList ei sisällä temporalLististä saatua alkiota,
			// se lisätään intersectionListiin ja sitä vastaava rivinumero
			// sijoitetaan rowListiin.
			if(!(intersectionList.contains(temporalList.get(i)))) {
				intersectionList.add(temporalList.get(i));
				rowList.add(temporalFileList.get(i));
			}
		}
		
		// Lähetä linkitetyt listat ja tiedoston nimi funktiolle, joka tekee
		// näistä tiedoston.
		writeOutput(intersectionList, rowList, "and.txt");
	}
	
	// Luo symmetrinen erotus kahden annetun linkitetyn listan välille ja pidä
	// kirjaa siitä, kummalla listalla alkio on esiintynyt.
	private void exclusive(LinkedList<String> valuesA, LinkedList<String> valuesB) {
		// Luo tyhjä linkitetty lista, johon lisätään alkiot, jotka ovat joko
		// listassa valuesA tai valuesB, mutta joka ei ole molemmissa listoissa
		// samaan aikaan.
		LinkedList<String> fileList = new LinkedList<String>();
		// Luo tyhjä linkitetty lista, johon sijoitetaan, kummalla listalla
		// alkio esiintyy.
		LinkedList<String> exclusiveList = new LinkedList<String>();
		
		// Käy läpi ensin listan valuesA alkiot suhteessa listaan valuesB.
		for (int i=0; i<valuesA.size(); i++) {
			// Jos alkio ei ole esiinny kummassakin listassa yhtä aikaa,
			// se sijoitetaan exclusiveListiin ja sijoitetaan merkki "1" listaan
			// fileListiin.
			if (!(valuesB.contains(valuesA.get(i)))) {
				exclusiveList.add(valuesA.get(i));
				fileList.add("1");
			}
		}
		// Käy läpi sitten listan valuesB alkiot suhteessa listaan valuesA.
		for (int i=0; i<valuesB.size(); i++) {
			// Jos alkio ei ole esiinny kummassakin listassa yhtä aikaa,
			// se sijoitetaan exclusiveListiin ja sijoitetaan merkki "2" listaan
			// fileListiin.
			if (!(valuesA.contains(valuesB.get(i)))) {
				exclusiveList.add(valuesB.get(i));
				fileList.add("2");
			}
		}
		
		// Lähetä linkitetyt listat ja tiedoston nimi funktiolle, joka tekee
		// näistä tiedoston.
		writeOutput(exclusiveList, fileList, "xor.txt");
	}

	// Kirjoita annetun tiedostonnimen alle kaksi annettua linkitettyä listaa
	private void writeOutput(LinkedList<String> column1, LinkedList<String> column2, String fileName) {
		try {
			// Luo kirjoittajaobjekti.
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < column1.size(); i++) {
				// Kirjoita listojen alkiot tiedostoon erottavan merkin "|" kera.
				bw.write(column1.get(i) + " | " + column2.get(i) + "\n");
			}
			// Sulje kirjoittajaobjekti.
			bw.close();
		} 
		catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}

	public static void main(String[] args) {
		logicalOperators ht=new logicalOperators();
		
		// Lue kaksi tekstitiedostoa ja sijoitetaan niiden rivit 
		// linkitettyihin listoihin.
		LinkedList<String> valuesA = ht.readInput("setA.txt");
		LinkedList<String> valuesB = ht.readInput("setB.txt");
		
		// Esittele ohjelma.
		System.out.println("This program receives two input files and creates three different set files out of them.");
		System.out.println("\n" + "The following set operations are being used: ");
		System.out.println("- union (OR)" + "\n" + "- intersection (AND)" + "\n" + "- symmetric difference (XOR)" + "\n");
		System.out.println("The two input files can be arbitrarily large." + "\n");
		
		// Poista alkio taulusta, mikäli käyttäjä haluaa ja palauta 
		// taulu takaisin muuttujaansa.
		valuesA = ht.deleteValue(valuesA);
		
		// Laske ja ilmoita alkioiden määrä.
		int sum = valuesA.size() + valuesB.size();
		System.out.println("\n" + "The total number of values: " + sum + "\n");
		
		// Informoi käyttäjää tiedoston luomisprosesseista.
		System.out.println("Writing files..." + "\n");
		// Luo linkitetty lista unionille ja luo sille tiedosto.
		ht.union(valuesA, valuesB);
		System.out.println("The file \"or.txt\" has been created.");
		// Luo linkitetty lista leikkaukselle ja luo sille tiedosto.
		ht.intersection(valuesA, valuesB);
		System.out.println("The file \"and.txt\" has been created.");
		// Luo linkitetty lista symmetriselle erotukselle (poissulkeva operaatio)
		// ja luo sille tiedosto
		ht.exclusive(valuesA, valuesB);
		System.out.println("The file \"xor.txt\" has been created." + "\n");
		System.out.println("All the files have been created succesfully!");
	}
}