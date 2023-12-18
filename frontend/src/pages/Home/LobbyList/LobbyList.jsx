import { useState } from "react";
import "./LobbyList.css";
import getAllLobbies from "./getAllLobbies";
import joinLobby from "./joinLobby";
import Table from "../../../components/Table/Table";

const LobbyList = ({ className }) => {
  const [lobbies, setLobbies] = useState([]);
  const [filters, setFilters] = useState({
    hidePasswordProtected: false,
    hideDefault: false,
  });

  const lobbyData = async () => {
    let data = await getAllLobbies();
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

  const handleRowClick = async (id) => {
    return joinLobby(id);
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
    { header: "Key", accessor: (row) => (row.hasPassword ? "🔒" : "") },
    { header: "Lobby Name", accessor: "lobbyName" },
    { header: "Owner", accessor: "ownerDisplayName" },
    {
      header: "Map",
      accessor: (row) => {
        return row.gameType === "ARAM" ? "Howling Abyss" : "Summoners Rift";
      },
    },
    {
      header: "Player",
      accessor: (row) => `${row.filledPlayerSlots}/${row.maxPlayerSlots}`,
    },
    {
      header: "Spectator",
      accessor: (row) => `${row.filledSpectatorSlots}/${row.maxSpectatorSlots}`,
    },
  ];

  return (
    <div className={className}>
      <div className="row my-1">
        <button
          type="button"
          className="btn btn-info col-3"
          onClick={lobbyData}
        >
          Refresh Lobbies
        </button>
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
        className="table table-sm table-info"
        columns={columns}
        data={lobbies}
        filters={applyFilters}
        handleRowDoubleClick={(row) => handleRowClick(row.id)}
      />
    </div>
  );
};

export default LobbyList;
