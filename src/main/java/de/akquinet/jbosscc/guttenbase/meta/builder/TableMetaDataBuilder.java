package de.akquinet.jbosscc.guttenbase.meta.builder;

import java.util.ArrayList;
import java.util.List;

import de.akquinet.jbosscc.guttenbase.meta.InternalTableMetaData;
import de.akquinet.jbosscc.guttenbase.meta.impl.TableMetaDataImpl;

/**
 * Builder class for TableMetaData.
 * 
 * <p>
 * &copy; 2012 akquinet tech@spree
 * </p>
 * 
 * @author M. Dahm
 */
public class TableMetaDataBuilder {
	private String _tableName = "FOO";
	private final List<ColumnMetaDataBuilder> _columns = new ArrayList<ColumnMetaDataBuilder>();
	private final List<IndexMetaDataBuilder> _indexes = new ArrayList<IndexMetaDataBuilder>();
	private final List<ForeignKeyMetaDataBuilder> _foreignKeys = new ArrayList<ForeignKeyMetaDataBuilder>();
	private final DatabaseMetaDataBuilder _databaseMetaDataBuilder;
	private TableMetaDataImpl _result;

	public TableMetaDataBuilder(final DatabaseMetaDataBuilder databaseMetaDataBuilder) {
		assert databaseMetaDataBuilder != null : "databaseMetaDataBuilder != null";
		_databaseMetaDataBuilder = databaseMetaDataBuilder;
	}

	public InternalTableMetaData build() {
		if (_result == null) {
			_result = new TableMetaDataImpl(_tableName, _databaseMetaDataBuilder.build());

			for (final ColumnMetaDataBuilder columnBuilder : _columns) {
				_result.addColumn(columnBuilder.build());
			}

			for (final IndexMetaDataBuilder indexBuilder : _indexes) {
				_result.addIndex(indexBuilder.build());
			}

			for (final ForeignKeyMetaDataBuilder foreignKeyMetaDataBuilder : _foreignKeys) {
				_result.addForeignKey(foreignKeyMetaDataBuilder.build());
			}
		}

		return _result;
	}

	public TableMetaDataBuilder setTableName(final String tableName) {
		_tableName = tableName;
		return this;
	}

	public TableMetaDataBuilder addColumn(final ColumnMetaDataBuilder columnMetaDataBuilder) {
		_columns.add(columnMetaDataBuilder);
		return this;
	}

	public TableMetaDataBuilder addIndex(final IndexMetaDataBuilder builder) {
		_indexes.add(builder);
		return this;
	}

	public TableMetaDataBuilder addForeignKey(final ForeignKeyMetaDataBuilder builder) {
		_foreignKeys.add(builder);
		return this;
	}

	public ColumnMetaDataBuilder getColumn(final String columnName) {
		for (final ColumnMetaDataBuilder builder : _columns) {
			if (builder.getColumnName().equalsIgnoreCase(columnName)) {
				return builder;
			}
		}

		return null;
	}
}
