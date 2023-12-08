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
    <div className="row">
      <div className="col-md-3">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Lobby Name</label>
            <input
              className="form-control"
              type="text"
              name="lobbyname"
              value={inputs.lobbyname}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input
              className="form-control"
              type="text"
              name="lobbypassword"
              value={inputs.lobbypassword}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label>Game Type</label>
            <select
              className="form-control"
              name="mapid"
              value={inputs.mapid}
              onChange={handleChange}
            >
              <option value={12}>Howling Abyss</option>
              <option value={11}>Summoners Rift</option>
            </select>
          </div>
          <div className="form-group">
            <label>Team Size</label>
            <select
              className="form-control"
              name="teamsize"
              value={inputs.teamsize}
              onChange={handleChange}
            >
              <option value={1}>1</option>
              <option value={2}>2</option>
              <option value={3}>3</option>
              <option value={4}>4</option>
              <option value={5}>5</option>
            </select>
          </div>
          <div className="form-group">
            <label>Spectator</label>
            <select
              className="form-control"
              name="spectatorpolicy"
              value={inputs.spectatorpolicy}
              onChange={handleChange}
            >
              <option value="AllAllowed">All</option>
              <option value="LobbyAllowed">Lobby</option>
              <option value="FriendsAllowed">Friends</option>
              <option value="NotAllowed">Not</option>
            </select>
            <input type="submit" className="btn btn-secondary" />
          </div>
        </form>
      </div>
    </div>
  );
};

export default LobbyForm;
