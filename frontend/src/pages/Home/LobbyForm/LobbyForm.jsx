import { useState, useEffect } from "react";
import createLobby from "./createLobby";
import Button from "../../../components/Button";

const LobbyForm = ({ className }) => {
  const defaultLobby = {
    lobbyname: "",
    lobbypassword: "",
    mapid: "12",
    teamsize: "1",
    spectatorpolicy: "AllAllowed",
  };

  const [inputs, setInputs] = useState(defaultLobby);
  const [presetIndex, setPresetIndex] = useState(0);
  const [presets, setPresets] = useState([
    defaultLobby,
    defaultLobby,
    defaultLobby,
  ]);

  const handleSubmit = (event) => {
    event.preventDefault();
    createLobby(inputs);
  };

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleSave = () => {
    const nextPresets = presets.map((c, i) => {
      if (i === presetIndex) return inputs;
      return c;
    });
    setPresets(nextPresets);
    localStorage.setItem("presets", JSON.stringify(nextPresets));
  };

  const handleLoad = () => {
    setInputs(presets[presetIndex]);
  };

  const handleClear = () => {
    setInputs(defaultLobby);
  };

  useEffect(() => {
    const savedPresets = localStorage.getItem("presets");
    if (savedPresets) setPresets(JSON.parse(savedPresets));
  }, []);

  return (
    <div className={className}>
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
          <div className="my-2">
            <Button
              text="Submit"
              type="submit"
              className="btn btn-secondary"
            ></Button>

            <Button
              text="Save"
              type="button"
              className="btn btn-info mx-2"
              onClick={handleSave}
            />

            <Button
              text="Load"
              type="button"
              className="btn btn-info mx-2"
              onClick={handleLoad}
            />

            <Button
              text="Clear"
              type="button"
              className="btn btn-info mx-2"
              onClick={handleClear}
            />
          </div>
        </div>
      </form>
      <div>
        <p className="mt-4">Presets</p>

        <Button
          className={`btn btn-info mx-2 ${
            presetIndex === 0 ? "bg-primary" : "bg-secondary"
          }`}
          onClick={() => setPresetIndex(0)}
          text="1"
        />

        <Button
          className={`btn btn-info mx-2 ${
            presetIndex === 1 ? "bg-primary" : "bg-secondary"
          }`}
          onClick={() => setPresetIndex(1)}
          text="2"
        />

        <Button
          className={`btn btn-info mx-2 ${
            presetIndex === 2 ? "bg-primary" : "bg-secondary"
          }`}
          onClick={() => setPresetIndex(2)}
          text="3"
        />
      </div>
    </div>
  );
};

export default LobbyForm;
