const Table = ({ name, className, columns, data }) => {
  const tableWrapper = {
    display: "block",
    maxHeight: "500px",
    overflowY: "auto",
    overflowX: "hidden",
  };
  const thead = { tableLayout: "fixed" };

  return (
    <div style={tableWrapper}>
      <table className={className}>
        <thead style={thead}>
          <tr>
            {columns.map((column, index) => (
              <th key={index}>{column.header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((row, rowIndex) => (
            <tr key={rowIndex}>
              {columns.map((column, columnIndex) => (
                <td key={columnIndex}>{row[column.accessor]}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Table;
