package ch.hood;

import static ch.hood.jooq.schema1.Tables.A;
import static ch.hood.jooq.schema2.Tables.B;
import static org.jooq.impl.DSL.select;

public class App {
	public static void main(String[] args) {
		System.out.println(
			select(A.ID, A.FLAG)
				.from(A)
				.join(B).on(B.NAME.eq(A.NAME))
				.toString());
	}

}
