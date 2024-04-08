/* eslint-disable react/prop-types */
const Table = ({ columns, data, className, filters, handleRowDoubleClick }) => {
  const totalRatio = columns.reduce((acc, column) => acc + column.widthRatio, 0);

  return (
    <div className="table-container">
      <table className={`table-auto ${className}`}>
        <thead>
          <tr>
            {columns.map((column, columnIndex) => (
              <th
                className="text-left select-none bg-green-800"
                key={columnIndex}
                style={{ width: `${(column.widthRatio / totalRatio) * 100}%` }}
              >
                {column.header}{" "}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data &&
            data.filter(filters).map((row, rowIndex) => (
              <tr
                className=" bg-blue-600 hover:bg-blue-700 select-none"
                key={rowIndex}
                onDoubleClick={() => handleRowDoubleClick(row)}
              >
                {columns.map((column, columnIndex) => (
                  <td className="" key={columnIndex}>
                    {typeof column.accessor === "function" ? column.accessor(row) : row[column.accessor]}
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
