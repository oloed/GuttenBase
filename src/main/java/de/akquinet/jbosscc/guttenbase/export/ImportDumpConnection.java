package de.akquinet.jbosscc.guttenbase.export;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import de.akquinet.jbosscc.guttenbase.exceptions.ImportException;
import de.akquinet.jbosscc.guttenbase.meta.TableMetaData;

/**
 * Special "{@link Connection}" that supports reading data from a stream.
 * 
 * <p>
 * &copy; 2012 akquinet tech@spree
 * </p>
 * 
 * @author M. Dahm
 */
public class ImportDumpConnection implements Connection {
	private final Importer _importer;
	private boolean _closed;
	private final Set<TableMetaData> _importedTables = new HashSet<TableMetaData>();
	private TableMetaData _currentTableMetaData;

	public ImportDumpConnection(final Importer importer) {
		assert importer != null : "importer != null";

		_importer = importer;
	}

	public void initializeReadTable(final TableMetaData table) {
		_currentTableMetaData = table;
	}

	public void finalizeReadTable(final TableMetaData table) {
		_currentTableMetaData = null;
	}

	/**
	 * Returns custom PreparedStatement statement.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public PreparedStatement prepareStatement(final String sql) throws SQLException {
		// Read header only once
		if (_importedTables.add(_currentTableMetaData)) {
			seekTableHeader(_currentTableMetaData);
		}

		return new ImportDumpPreparedStatement(_importer, _currentTableMetaData);
	}

	private void seekTableHeader(final TableMetaData tableMetaData) throws ImportException {
		try {
			_importer.seekTableHeader(tableMetaData);
		} catch (final Exception e) {
			throw new ImportException("getObject", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws SQLException {
		try {
			_importer.finishImport();
		} catch (final Exception e) {
			throw new ImportException("close", e);
		} finally {
			_closed = true;
			_importedTables.clear();
		}
	}

	@Override
	public boolean isClosed() {
		return _closed;
	}

	@Override
	public void commit() throws SQLException {
	}

	@Override
	public void setAutoCommit(final boolean autoCommit) throws SQLException {
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return false;
	}

	@Override
	public void rollback() throws SQLException {
	}

	@Override
	public void setReadOnly(final boolean readOnly) throws SQLException {
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return true;
	}

	@Override
	public Statement createStatement() throws SQLException {
		return prepareStatement("");
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(final String sql) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String nativeSQL(final String sql) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCatalog(final String catalog) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalog() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setTransactionIsolation(final int level) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {

		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHoldability(final int holdability) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {

		return 0;
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Savepoint setSavepoint(final String name) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void rollback(final Savepoint savepoint) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)
			throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency,
			final int resultSetHoldability) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency,
			final int resultSetHoldability) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final String[] columnNames) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Clob createClob() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Blob createBlob() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public NClob createNClob() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isValid(final int timeout) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setClientInfo(final Properties properties) throws SQLClientInfoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getClientInfo(final String name) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Properties getClientInfo() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {

		throw new UnsupportedOperationException();
	}
}
