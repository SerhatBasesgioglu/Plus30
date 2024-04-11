import { useState } from "react";
import Button from "components/Button";
import { get } from "services/api";
import KickList from "./KickList";

const LobbySettings = () => {
  const [kickBlocked, setKickBlocked] = useState(true);
  const [kickBlackList, setKickBlackList] = useState(true);

  const handleBlockedToggle = () => {
    setKickBlocked(!kickBlocked);
    if (kickBlocked) {
      get("/lobby/start-kicker");
    } else {
      get("/lobby/stop-kicker");
    }
    console.log(kickBlocked);
  };
  const handleBlackListToggle = () => {
    setKickBlackList(!kickBlackList);
  };

  return (
    <div className="mt-4">
      <Button
        className={!kickBlocked ? "bg-green-400 hover:bg-green-500" : "bg-red-400 hover:bg-red-500"}
        text="Kick Blocked"
        onClick={handleBlockedToggle}
      />
      <Button
        className={!kickBlackList ? "bg-green-400 hover:bg-green-500" : "bg-red-400 hover:bg-red-500"}
        text="Kick Blacklisted"
        onClick={handleBlackListToggle}
      />
      <KickList />
    </div>
  );
};

export default LobbySettings;
