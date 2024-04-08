/* eslint-disable react/prop-types */
import Button from "components/Button";
const Popup = ({ text, onClick }) => {
  return (
    <div className="fixed top-0 left-0 w-full h-full flex justify-center items-center z-50">
      <div className="bg-gray-900 bg-opacity-50 absolute inset-0 flex justify-center items-center">
        <div className="bg-green-300 px-2 flex flex-col justify-center items-center rounded-lg">
          <p>{text}</p>
          <Button className="" text="Close" onClick={onClick} />
        </div>
      </div>
    </div>
  );
};

export default Popup;
