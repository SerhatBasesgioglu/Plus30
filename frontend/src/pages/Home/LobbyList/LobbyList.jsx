/* eslint-disable react/prop-types */
import { useState } from "react";
import Table from "components/Table";
import Button from "components/Button";
import Popup from "components/Popup";
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
  };

  const [isPopupActive, setIsPopupActive] = useState(false);
  const [popupMessage, setPopupMessage] = useState("Default popup message");

  const handlePopupClose = () => {
    setIsPopupActive(false);
  };

  const joinLobby = async (lobby) => {
    try {
      await post("/lobby/join", { lobbyId: lobby.id });
    } catch (error) {
      const statusCode = error.response.data.status;
      if (statusCode === 404 || statusCode === 530) setPopupMessage("This lobby is not available anymore");
      else if (statusCode === 403) setPopupMessage("This lobby has password");
      else if (statusCode === 432) setPopupMessage("This lobby is full");
      setIsPopupActive(true);
    }
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
      header: "🔒",
      accessor: (row) => (row.hasPassword ? "🔒" : ""),
      widthRatio: 2,
    },
    { header: "Lobby Name", accessor: (row) => row.lobbyName, widthRatio: 15 },
    {
      header: "Owner",
      accessor: (row) => row.ownerDisplayName,
      widthRatio: 15,
    },
    {
      header: "Map",
      accessor: (row) => (row.gameType === "ARAM" ? "Howling Abyss" : "Summoners Rift"),
      widthRatio: 10,
    },
    {
      header: "Player",
      accessor: (row) => `${row.filledPlayerSlots}/${row.maxPlayerSlots}`,
      widthRatio: 3,
    },
    {
      header: "Spectator",
      accessor: (row) => `${row.filledSpectatorSlots}/${row.maxSpectatorSlots}`,
      widthRatio: 3,
    },
  ];

  return (
    <div className={className}>
      {isPopupActive && <Popup text={popupMessage} onClick={handlePopupClose} />}
      <div className="">
        <Button text="Refresh" className="bg-yellow-500 hover:bg-yellow-600" onClick={lobbyData} />
        <Button
          text="Default Lobbies"
          className={isDefaultChecked ? "bg-green-400 hover:bg-green-500" : "bg-red-400 hover:bg-red-500"}
          onClick={handleDefaultToggle}
        />
        <Button
          text="Pass Lobbies"
          className={isPasswordChecked ? "bg-green-400 hover:bg-green-500" : "bg-red-400 hover:bg-red-500"}
          onClick={handlePasswordToggle}
        />
      </div>
      <Table
        className="text-xs"
        columns={columns}
        data={lobbies}
        filters={applyFilters}
        handleRowDoubleClick={(row) => joinLobby(row)}
      />
    </div>
  );
};

export default LobbyList;
