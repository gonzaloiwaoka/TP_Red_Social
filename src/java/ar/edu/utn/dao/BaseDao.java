package ar.edu.utn.dao;

import ar.edu.utn.error.OpErrorManagement;
import java.sql.*;
import java.util.*;


public abstract class BaseDao {

    private Connection connection;
    private String connectionString;
    private String password;
    private String user;
    private OpErrorManagement opErrorMgmt;
           
    private final static int SQL_EXCEPTION_WRITE = 5;
    private final static int EXCEPTION_WRITE = 6;
    private final static int EXCEPTION_RESULT_SET = 7;
    
    
    public BaseDao(String connectionString, String user, String password, OpErrorManagement opErrorMgmt) {
        this.connectionString = connectionString;
        this.password = password;
        this.user = user;
        this.opErrorMgmt = opErrorMgmt;
    }

    private Connection getConnection() {
        try {
            if (connection == null){
                connection = DriverManager.getConnection(connectionString, user, password);
                // TODO: analizar las implicancias de una transacción
                // connection.setAutoCommit(false);
            }
        } catch (SQLException exception) {
            // TODO - hacer algo con el error
        }
        return connection;
    }
    
    /**
     * Ejecuta executeUpdate para realizar las operaciones de inserciÃ³n, actualizaciÃ³n y eliminaciÃ³n.
     * @param sql
     * @param parameters
     * @return
     */
    protected int write(String sql, Map<Integer, Object> parameters) {
        PreparedStatement preparedStatement = null;
        int returnedValue = 0;
        
        try {
            preparedStatement = getStatement(sql, parameters, getConnection());
            returnedValue = preparedStatement.executeUpdate();
                
            // En el caso de un UPDATE o DELETE, se devuelve la cantidad de registros afectados.
            // En el caso de un INSERT, se devuelve el id generado
            if (returnedValue > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    // TODO: es posible cambiar esta estructura por una expresión ternaria
                    if (generatedKeys.next()) {
                        returnedValue = generatedKeys.getInt(1);
                    }
                } catch (Exception exception) {
                    // TODO: analizar qué ocurre si se loguea por la salida standard
                    System.out.println("No keys to be retrieved");
                }
            }

        } catch (SQLException exception) {
            opErrorMgmt.addError(SQL_EXCEPTION_WRITE, exception.getMessage());
        } catch (Exception exception) {
            opErrorMgmt.addError(EXCEPTION_WRITE, exception.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch ( Exception e) {
                opErrorMgmt.addError(EXCEPTION_RESULT_SET, e.getMessage());
            }
            return returnedValue;
        }
    }
    /*
    protected List<Map<String, Object>> read(String sql, Map<Integer, Object> parameters) {

        List<Map<String, Object>> results = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            Connection con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, parameters.get(1).toString());
            //preparedStatement = getStatement(sql, parameters, getConnection());
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            ResultSetMetaData metadata = resultSet.getMetaData();

            // Mediante los metadatos, es posible obtener el nombre de la columna y el valor; si consideramos un
            // registro como un conjunto de columnas con sus valores, es posible modelarlo con un map.
            // Adicionalmente, como la consulta puede devolver varios registros, se emplea una lista para guardarlos,
            // ya que mantiene el orden y es fÃ¡cil de recorrer.
            while(resultSet.next()) {
                HashMap<String, Object> columns = new HashMap<>();

                for (int i = 1; i <= metadata.getColumnCount(); ++i) {
                    columns.put(metadata.getColumnName(i), resultSet.getObject(i));
                }
                results.add(columns);
            }

        } catch (SQLException exception) {
            //addError("SQLException:Read", exception);
        }  catch (Exception exception) {
            //addError("Exception:Read", exception);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch ( Exception e) {
                //addError("Exception:ResultSet", e);
            }
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch ( Exception e) {
                //addError("Exception:ResultSet", e);
            }
            return results;
        }
    }*/
    protected List<Map<String, Object>> read(String sql, Map<Integer, Object> parameters) {

        List<Map<String, Object>> results = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = getStatement(sql, parameters, getConnection());

            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metadata = resultSet.getMetaData();

            // Mediante los metadatos, es posible obtener el nombre de la columna y el valor; si consideramos un
            // registro como un conjunto de columnas con sus valores, es posible modelarlo con un map.
            // Adicionalmente, como la consulta puede devolver varios registros, se emplea una lista para guardarlos,
            // ya que mantiene el orden y es fÃ¡cil de recorrer.
            while(resultSet.next()) {
                HashMap<String, Object> columns = new HashMap<>();

                for (int i = 1; i <= metadata.getColumnCount(); ++i) {
                    columns.put(metadata.getColumnName(i), resultSet.getObject(i));
                }
                results.add(columns);
            }

        } catch (SQLException exception) {
            opErrorMgmt.addError(SQL_EXCEPTION_WRITE, exception.getMessage());
        }  catch (Exception exception) {
            opErrorMgmt.addError(EXCEPTION_WRITE, exception.getLocalizedMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch ( Exception e) {
                opErrorMgmt.addError(EXCEPTION_RESULT_SET, e.getMessage());
            }
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch ( Exception e) {
                opErrorMgmt.addError(EXCEPTION_RESULT_SET, e.getMessage());
            }
            return results;
        }
    }
    
    
    private PreparedStatement getStatement(String sql, Map<Integer, Object> parameters, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // Al usar prepared statement se garantiza seguridad contra sql injection, pero es necesario
        // implementar una estrategia para el reemplazo de los "wildcards" por los valores deseados
        if (parameters != null) {
            Set<Integer> keys = parameters.keySet();
            for (Integer key : keys) {
                Object value = parameters.get(key);
                System.out.print(key + " = " + value + ", ");
                preparedStatement.setObject(key, value);
            }
        }
        
        return preparedStatement;
    }

    public OpErrorManagement getOpErrorMgmt() {
        return opErrorMgmt;
    }
    
    
    
}
