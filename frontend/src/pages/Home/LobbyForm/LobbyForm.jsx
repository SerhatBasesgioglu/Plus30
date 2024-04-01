/* eslint-disable react/prop-types */
import { useState, useEffect } from "react";
import Button from "components/Button";
import { post } from "services/api";

const LobbyForm = ({ className }) => {
  const defaultLobby = {
    lobbyName: "",
    lobbyPassword: "",
    mapId: "12",
    teamSize: "1",
    spectatorPolicy: "AllAllowed",
  };

  const [inputs, setInputs] = useState(defaultLobby);
  const [presetIndex, setPresetIndex] = useState(0);
  const [presets, setPresets] = useState([defaultLobby, defaultLobby, defaultLobby]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    await post("/lobby", inputs);
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
    <div className="p-2 flex flex-col justify-center items-center">
      <form className="" onSubmit={handleSubmit}>
        <div className="">
          <label className="block font-medium">Lobby Name</label>
          <input
            className="border border-gray-400 w-40"
            type="text"
            name="lobbyName"
            value={inputs.lobbyName}
            onChange={handleChange}
          />
        </div>
        <div className="">
          <label className="block font-medium">Password</label>
          <input
            className="border border-gray-400 w-40"
            type="text"
            name="lobbyPassword"
            value={inputs.lobbyPassword}
            onChange={handleChange}
          />
        </div>
        <div className="">
          <label className="block font-medium">Game Type</label>
          <select className="border border-gray-400 w-40" name="mapId" value={inputs.mapId} onChange={handleChange}>
            <option value={12}>Howling Abyss</option>
            <option value={11}>Summoners Rift</option>
          </select>
        </div>
        <div className="">
          <label className="block font-medium">Team Size</label>
          <select
            className="border border-gray-400 w-40"
            name="teamSize"
            value={inputs.teamSize}
            onChange={handleChange}
          >
            <option value={1}>1</option>
            <option value={2}>2</option>
            <option value={3}>3</option>
            <option value={4}>4</option>
            <option value={5}>5</option>
          </select>
        </div>
        <div className="">
          <label className="block font-medium">Spectator</label>
          <select
            className="border border-gray-400 w-40"
            name="spectatorPolicy"
            value={inputs.spectatorPolicy}
            onChange={handleChange}
          >
            <option value="AllAllowed">All</option>
            <option value="LobbyAllowed">Lobby</option>
            <option value="FriendsAllowed">Friends</option>
            <option value="NotAllowed">Not</option>
          </select>
          <div className="my-1">
            <Button text="Submit" type="submit" className=""></Button>
            <Button text="Save" type="button" className="" onClick={handleSave} />
            <Button text="Load" type="button" className="" onClick={handleLoad} />
            <Button text="Clear" type="button" className="" onClick={handleClear} />
          </div>
        </div>
      </form>
      <div>
        <p className="mt-2">Presets</p>

        <Button className={`${presetIndex === 0 ? "bg-blue-800" : ""}`} onClick={() => setPresetIndex(0)} text="1" />

        <Button className={`${presetIndex === 1 ? "bg-blue-800" : ""}`} onClick={() => setPresetIndex(1)} text="2" />

        <Button className={`${presetIndex === 2 ? "bg-blue-800" : ""}`} onClick={() => setPresetIndex(2)} text="3" />
      </div>
    </div>
  );
};

export default LobbyForm;
