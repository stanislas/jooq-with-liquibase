package ch.hood;

import static ch.hood.jooq.schema1.Schema1.SCHEMA1;
import static ch.hood.jooq.schema1.Tables.A;
import static ch.hood.jooq.schema2.Schema2.SCHEMA2;
import static ch.hood.jooq.schema2.Tables.B;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.jooq.impl.DSL.select;

import ch.hood.jooq.schema1.Schema1;
import ch.hood.jooq.schema2.Schema2;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

/**
 * The main methods expects either no arguments or 2 arguments. When run without arguments, we assume a HQSLDB;
 * when run with 2 arguments, we expect the names of the schema1 and the schema2 for a Postgresql (or Oracle) instance.
 */
public class App {
	public static void main(String[] args) {
		SQLDialect sqlDialect = args.length == 0 ? SQLDialect.HSQLDB : SQLDialect.POSTGRES; // SQLDialect.ORACLE

		Settings settings = new Settings()
			.withRenderFormatted(true)
			.withRenderSchema(TRUE)
			.withRenderNameStyle(RenderNameStyle.UPPER);
		if (sqlDialect == SQLDialect.POSTGRES) {
			String schema1Name = args[0];
			String schema2Name = args[1];
			settings.withRenderMapping(new RenderMapping()
				.withSchemata(
					new MappedSchema().withInput(SCHEMA1.getName()).withOutput(schema1Name),
					new MappedSchema().withInput(SCHEMA2.getName()).withOutput(schema2Name)));
		}
		
		Configuration config = new DefaultConfiguration()
			.set(sqlDialect)
			.set(settings);

		Configuration configuration = config;
		DSLContext dsl = DSL.using(configuration);

		System.out.println(
			dsl.select(A.ID, A.FLAG)
				.from(A)
				.join(B).on(B.NAME.eq(A.NAME))
				.toString());
	}

}
