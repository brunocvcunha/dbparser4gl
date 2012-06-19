package org.bruno.dbparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bruno.dbparser.vo.Field;
import org.bruno.dbparser.vo.Table;

public class GetDefaultTables {

	public static boolean IS_VERBOSE = true;

	public static Collection<Table> listTables() {
		return listTables(new File("\\\\vigoreli\\ExpDtsul4\\@byyou\\11.5.2\\database\\progress\\ems2\\dffiles"));
	}

	public static Collection<Table> listTables(String f) {
		return listTables(new File(f));
	}

	public static Collection<Table> listTables(File f) {
		DatabaseParser parser = new DatabaseParser();
		recursiveParser(parser, f);
		return parser.getTables();
	}

	public static Collection<Table> listTables(File[] a) {
		Collection<Table> tableList = new ArrayList<Table>();

		for (File f : a) {
			DatabaseParser parser = new DatabaseParser();
			recursiveParser(parser, f);
			tableList.addAll(parser.getTables());
		}

		return tableList;
	}

	public static void recursiveParser(DatabaseParser parser, File dir) {
		if (dir.isDirectory()) {
			for (File df : dir.listFiles()) {
				recursiveParser(parser, df);
			}
		} else {
			if (dir.getName().endsWith(".df")) {
				if (IS_VERBOSE) {
					System.out.println("[+] Parsing " + dir.getAbsolutePath() + "...");
				}
				parser.parseDefinitions(dir, dir.getName().split("\\.")[0]);
			}
		}

	}

	public static Table tableForName(Collection<Table> tables, String name) {
		for (Table t : tables) {
			if (t.getName().equals(name))
				return t;
		}

		return null;
	}

	public static Table tableForDump(Collection<Table> tables, String dump) {
		for (Table t : tables) {
			if (t.getDump().equals(dump))
				return t;
		}

		return null;
	}

	public static boolean tableHasField(Table table, String field) {
		for (Field f : table.getFields()) {
			if (f.getName().equals(field))
				return true;
		}

		return false;
	}

	public static boolean validField(Collection<Table> tables, String field) {
		for (Table t : tables) {
			if (t.getName().toLowerCase().equals(field.split("\\.")[0].toLowerCase())) {

				for (Field f : t.getFields()) {
					if (f.getName().toLowerCase().equals(field.split("\\.")[1].toLowerCase())) {
						return true;
					}
				}

				return false;
			}
		}

		return false;
	}

	public static boolean validField(Table t, String field) {

		for (Field f : t.getFields()) {
			if (f.getName().toLowerCase().equals(field.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

}