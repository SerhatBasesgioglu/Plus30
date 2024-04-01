/* eslint-disable react/prop-types */
import LobbyForm from "./LobbyForm";
import LobbyList from "./LobbyList";
import LobbySettings from "./LobbySettings";

import { useState } from "react";
import Button from "components/Button";
const style = "text-xs bg-zinc-600";

const Home = () => {
  const [activeTab, setActiveTab] = useState(1);

  const handleTabClick = (tabNum) => {
    setActiveTab(tabNum);
  };
  return (
    <div className={style}>
      <div className="flex justify-center">
        <Button text="Create Lobby" onClick={() => handleTabClick(1)} />
        <Button text="Lobby List" onClick={() => handleTabClick(2)} />
        <Button text="Lobby Settings" onClick={() => handleTabClick(3)} />
      </div>
      {activeTab === 1 && <LobbyForm />}
      {activeTab === 2 && <LobbyList />}
      {activeTab === 3 && <LobbySettings />}
    </div>
  );
};

export default Home;
