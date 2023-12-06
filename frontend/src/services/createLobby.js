import axios from "axios";

const createLobby = async (lobbyData) => {
  console.log("Hey");
  try {
    const response = await axios.post("http://localhost:8080/lobby/create", {
      lobbyName: lobbyData.lobbyname,
      lobbyPassword: lobbyData.lobbypassword,
    });
    console.log(response);
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

export default createLobby;
