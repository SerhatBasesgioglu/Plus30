import { useState } from "react";
import "./LobbyList.css";
import Table from "components/Table/Table";
import Button from "components/Button";
import { get, post } from "services/api";

const LobbyList = ({ className }) => {
  const [lobbies, setLobbies] = useState([]);
  const [filters, setFilters] = useState({
    hidePasswordProtected: false,
    hideDefault: false,
  });

  const lobbyData = async () => {
    let data = await get("/lobby/all-lobbies");
    data.sort((a, b) => {
      if (a.lobbyName < b.lobbyName) {
        return -1;
      }
      if (a.lobbyName > b.lobbyName) {
        return 1;
      }
      return 0;
    });
    setLobbies(data);
    console.log(data);
  };

  const joinLobby = async (id) => {
    return post("/lobby/join", { lobbyId: id });
  };

  const applyFilters = (lobby) => {
    if (filters.hidePasswordProtected && lobby.hasPassword) return false;
    if (
      (filters.hideDefault && lobby.lobbyName.includes("oyunu")) ||
      lobby.lobbyName.includes("game")
    )
      return false;
    return true;
  };

  const columns = [
    {
      header: "Key",
      accessor: (row) => (row.hasPassword ? "🔒" : ""),
      widthRatio: 3,
    },
    { header: "Lobby Name", accessor: (row) => row.lobbyName, widthRatio: 15 },
    {
      header: "Owner",
      accessor: (row) => row.ownerDisplayName,
      widthRatio: 10,
    },
    {
      header: "Map",
      accessor: (row) =>
        row.gameType === "ARAM" ? "Howling Abyss" : "Summoners Rift",
      widthRatio: 15,
    },
    {
      header: "Player",
      accessor: (row) => `${row.filledPlayerSlots}/${row.maxPlayerSlots}`,
      widthRatio: 5,
    },
    {
      header: "Spectator",
      accessor: (row) => `${row.filledSpectatorSlots}/${row.maxSpectatorSlots}`,
      widthRatio: 5,
    },
  ];

  return (
    <div className={className}>
      <div className="row my-1">
        <Button
          className="btn btn-info  col-3"
          type="button"
          onClick={lobbyData}
          text="Refresh Lobbies"
        />

        <div className="form-group col-4">
          <label>Hide password lobbies</label>
          <input
            type="checkbox"
            checked={filters.hidePasswordProtected}
            onChange={(e) =>
              setFilters({
                ...filters,
                hidePasswordProtected: e.target.checked,
              })
            }
          />
        </div>
        <div className="form-group col-4">
          <label>Hide default lobbies</label>
          <input
            type="checkbox"
            checked={filters.hideDefault}
            onChange={(e) => {
              setFilters({ ...filters, hideDefault: e.target.checked });
            }}
          />
        </div>
      </div>

      <Table
        className="table table-sm table-info table-hover"
        columns={columns}
        data={lobbies}
        filters={applyFilters}
        handleRowDoubleClick={(row) => joinLobby(row.id)}
      />
    </div>
  );
};

export default LobbyList;
