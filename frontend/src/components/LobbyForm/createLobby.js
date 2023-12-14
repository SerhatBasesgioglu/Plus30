import axios from "axios";

const createLobby = async (lobbyData) => {
  console.log(lobbyData);
  try {
    const response = await axios.post("http://localhost:8080/lobby/create", {
      lobbyName: lobbyData.lobbyname,
      lobbyPassword: lobbyData.lobbypassword,
      mapId: lobbyData.mapid,
      teamSize: lobbyData.teamsize,
      spectatorPolicy: lobbyData.spectatorpolicy,
    });
    console.log(response);
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

export default createLobby;
