import { useState } from "react";
import createLobby from "../services/createLobby";

const LobbyForm = () => {
  const [inputs, setInputs] = useState({
    lobbyname: "",
    lobbypassword: "",
    mapid: "12",
    teamsize: "1",
    spectatorpolicy: "AllAllowed",
  });

  const handleSubmit = (event) => {
    event.preventDefault();
    createLobby(inputs);
  };

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((prevState) => ({ ...prevState, [name]: value }));
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Lobby Name
        <input
          type="text"
          name="lobbyname"
          value={inputs.lobbyname}
          onChange={handleChange}
        />
      </label>
      <label>
        Password
        <input
          type="text"
          name="lobbypassword"
          value={inputs.lobbypassword}
          onChange={handleChange}
        />
      </label>
      <label>
        Game Type
        <select name="mapid" value={inputs.mapid} onChange={handleChange}>
          <option value={12}>Howling Abyss</option>
          <option value={11}>Summoners Rift</option>
        </select>
      </label>
      <label>
        Team Size
        <select name="teamsize" value={inputs.teamsize} onChange={handleChange}>
          <option value={1}>1</option>
          <option value={2}>2</option>
          <option value={3}>3</option>
          <option value={4}>4</option>
          <option value={5}>5</option>
        </select>
      </label>
      <label>
        Spectator
        <select
          name="spectatorpolicy"
          value={inputs.spectatorpolicy}
          onChange={handleChange}
        >
          <option value="AllAllowed">All</option>
          <option value="LobbyAllowed">Lobby</option>
          <option value="FriendsAllowed">Friends</option>
          <option value="NotAllowed">Not</option>
        </select>
      </label>
      <input type="submit" />
    </form>
  );
};

export default LobbyForm;
