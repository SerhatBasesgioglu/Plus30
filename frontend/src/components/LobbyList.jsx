import { useState } from "react";
import getAllLobbies from "../services/getAllLobbies";

const LobbyList = () => {
  const [lobbies, setLobbies] = useState([]);
  const [filters, setFilters] = useState({});
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

  return (
    <div>
      <button type="button" className="btn btn-info" onClick={lobbyData}>
        Click Me
      </button>
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
              lobbies.map((lobby) => (
                <tr key={lobby.id}>
                  <td>{lobby.hasPassword}</td>
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
