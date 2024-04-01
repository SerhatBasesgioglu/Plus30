import { get } from "services/api";

import Button from "components/Button";

const Miscellaneous = () => {
  const restartClient = () => {
    get("/client/reset");
  };

  return (
    <div>
      <Button className="bg-yellow-300" text="Restart Client" onClick={restartClient} />
    </div>
  );
};

export default Miscellaneous;
