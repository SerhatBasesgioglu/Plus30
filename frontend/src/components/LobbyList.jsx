import { useState } from "react";
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
    <div>
      <button type="button" className="btn btn-info" onClick={lobbyData}>
        Refresh Lobbies
      </button>
      <div className="form-group">
        <label>Hide password lobbies</label>
        <input
          type="checkbox"
          checked={filters.hidePasswordProtected}
          onChange={(e) =>
            setFilters({ ...filters, hidePasswordProtected: e.target.checked })
          }
        />
      </div>
      <div className="form-group">
        <label>Hide default lobbies</label>
        <input
          type="checkbox"
          checked={filters.hideDefault}
          onChange={(e) => {
            setFilters({ ...filters, hideDefault: e.target.checked });
          }}
        />
      </div>

      <div>
        <table className="table-sm table-bordered">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col">Lobby Name</th>
              <th scope="col">Owner</th>
              <th scope="col">Map</th>
              <th scope="col">Player</th>
              <th scope="col">Spectator</th>
            </tr>
          </thead>
          <tbody>
            {lobbies &&
              lobbies.filter(applyFilters).map((lobby) => (
                <tr key={lobby.id} onClick={() => handleRowClick(lobby.id)}>
                  <td>{lobby.hasPassword && "key"}</td>
                  <td>{lobby.lobbyName}</td>
                  <td>{lobby.ownerDisplayName}</td>
                  <td>{lobby.gameType}</td>
                  <td>
                    {lobby.filledPlayerSlots}/{lobby.maxPlayerSlots}
                  </td>
                  <td>
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
