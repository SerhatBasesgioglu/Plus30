import { useState } from "react";
import "../styles/LobbyList.css";
import getAllLobbies from "../services/getAllLobbies";
import joinLobby from "../services/joinLobby";

const LobbyList = () => {
  const [lobbies, setLobbies] = useState([]);
  const [filters, setFilters] = useState({
    hidePasswordProtected: false,
    hideDefault: false,
  });
  let data;

  const lobbyData = async () => {
    data = await getAllLobbies();
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

  return (
    <div className="col-6">
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

      <div className="table-responsive" style={{ height: "600px" }}>
        <table className="table table-sm table-bordered table-striped">
          <thead>
            <tr>
              <th className="col-key"></th>
              <th className="col-lobby-name">Lobby Name</th>
              <th className="col-lobby-owner">Owner</th>
              <th className="col-map-name">Map</th>
              <th className="col-player">Player</th>
              <th className="col-spectator">Spectator</th>
            </tr>
          </thead>
          <tbody style={{ height: "300px" }}>
            {lobbies &&
              lobbies.filter(applyFilters).map((lobby) => (
                <tr
                  key={lobby.id}
                  onDoubleClick={() => handleRowClick(lobby.id)}
                >
                  <td className="col-key">{lobby.hasPassword && "key"}</td>
                  <td className="col-lobby-name">{lobby.lobbyName}</td>
                  <td className="col-lobby-owner">{lobby.ownerDisplayName}</td>
                  <td className="col-map-name">{lobby.gameType}</td>
                  <td className="col-player">
                    {lobby.filledPlayerSlots}/{lobby.maxPlayerSlots}
                  </td>
                  <td className="col-spectator">
                    {lobby.filledSpectatorSlots}/{lobby.maxSpectatorSlots}
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default LobbyList;
