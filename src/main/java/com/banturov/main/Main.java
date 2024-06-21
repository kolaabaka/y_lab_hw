package com.banturov.main;

import java.util.Scanner;

import com.banturov.interaction.EventLayer;

public class Main {

	public static void main(String[] args) {
		Scanner inputScan = new Scanner(System.in);
		EventLayer.eventPage(inputScan);
	}

}
