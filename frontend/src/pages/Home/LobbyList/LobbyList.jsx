/* eslint-disable react/prop-types */
import { useState } from "react";
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
    let data = await get("/lobby/custom-games");
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
    if ((filters.hideDefault && lobby.lobbyName.includes("oyunu")) || lobby.lobbyName.includes("Game")) return false;
    return true;
  };

  const [isDefaultChecked, setIsDefaultChecked] = useState(true);
  const [isPasswordChecked, setIsPasswordChecked] = useState(true);

  const handleDefaultToggle = () => {
    setIsDefaultChecked(!isDefaultChecked);
    setFilters({ ...filters, hideDefault: isDefaultChecked });
  };

  const handlePasswordToggle = () => {
    setIsPasswordChecked(!isPasswordChecked);
    setFilters({ ...filters, hidePasswordProtected: isPasswordChecked });
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
      accessor: (row) => (row.gameType === "ARAM" ? "Howling Abyss" : "Summoners Rift"),
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
      <div className="">
        <Button className="bg-yellow-500" onClick={lobbyData} text="Refresh" />

        <Button
          text="Default Lobbies"
          className={isDefaultChecked ? "bg-green-400" : "bg-red-400"}
          onClick={handleDefaultToggle}
        />
        <Button
          text="Pass Lobbies"
          className={isPasswordChecked ? "bg-green-400" : "bg-red-400"}
          onClick={handlePasswordToggle}
        />
      </div>

      <Table
        className=""
        columns={columns}
        data={lobbies}
        filters={applyFilters}
        handleRowDoubleClick={(row) => joinLobby(row.id)}
      />
    </div>
  );
};

export default LobbyList;
