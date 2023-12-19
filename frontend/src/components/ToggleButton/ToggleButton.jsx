const ToggleButton = ({ isToggled, onToggle, ...props }) => {
  return (
    <button onClick={onToggle} {...props}>
      {isToggled ? "On" : "Off"}
    </button>
  );
};

export default ToggleButton;
