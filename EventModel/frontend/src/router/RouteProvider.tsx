import React, { PropsWithChildren, useState } from 'react';

enum RouteType {
  MAIN_PAGE = 'MainPage',
  DEMO_PAGE = 'DemoPage',
  NO_MATCH_PAGE = 'NoMatchPage',
}

interface RouteContextType {
  route: RouteType;
  setRoute: (route: RouteType) => void;
}

const RouteContext = React.createContext<RouteContextType>(null!);

const RouteProvider: React.FC<PropsWithChildren> = ({ children }) => {
  const [route, setRoute] = useState<RouteType>(RouteType.MAIN_PAGE);
  return (
    <RouteContext.Provider
      value={{
        route,
        setRoute,
      }}
    >
      {children}
    </RouteContext.Provider>
  );
};

export const useRoute = () => React.useContext(RouteContext);
export { RouteType };
export default RouteProvider;
