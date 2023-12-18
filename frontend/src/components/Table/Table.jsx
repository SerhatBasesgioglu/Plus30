import "./Table.css";
const Table = ({ columns, data, className, filters, handleRowDoubleClick }) => {
  return (
    <div className="table-container">
      <table className={className}>
        <thead>
          <tr>
            {columns.map((column, columnIndex) => (
              <th key={columnIndex}>{column.header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data &&
            data.filter(filters).map((row, rowIndex) => (
              <tr
                key={rowIndex}
                onDoubleClick={() => handleRowDoubleClick(row)}
              >
                {columns.map((column, columnIndex) => (
                  <td key={columnIndex}>
                    {typeof column.accessor === "function"
                      ? column.accessor(row)
                      : row[column.accessor]}
                  </td>
                ))}
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  );
};

export default Table;
