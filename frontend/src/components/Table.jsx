const Table = ({ name, className, columns, data }) => {
  return (
    <table className={className}>
      <thead>
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
  );
};

export default Table;

/* mock data for table, will replace the other tables with this later on 

  const columns = [
    {
      header: "Key",
      accessor: "key",
    },
    {
      header: "Lobby Name",
      accessor: "lobbyName",
    },
    {
      header: "Owner",
      accessor: "owner",
    },
    {
      header: "Map",
      accessor: "map",
    },
    {
      header: "Player",
      accessor: "player",
    },
    {
      header: "Spectator",
      accessor: "spectator",
    },
  ];

  const dataa = [
    {
      key: "key",
      lobbyName: "AyDakar Lobby",
      owner: "AyDaKaR",
      map: "map",
      player: "player",
      spectator: "spectator",
    },
  ];

  */
