import Table from "../../components/Table/Table";

const Test = () => {
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

  const data1 = {
    key: "key",
    lobbyName: "AyDakar Lobby",
    owner: "AyDaKaR",
    map: "map",
    player: "player",
    spectator: "spectator",
  };
  const data2 = {
    key: "zkey",
    lobbyName: "zAyDakar Lobby",
    owner: "zAyDaKaR",
    map: "zmap",
    player: "zplayer",
    spectator: "zspectator",
  };

  let dataarray = [];

  for (let x = 0; x < 100; x++) {
    dataarray[x] = x < 50 ? data1 : data2;
  }

  return (
    <div>
      <p> This is the testing ground for the components</p>
      <Table columns={columns} data={dataarray} className="table table-sm" />
    </div>
  );
};

export default Test;
