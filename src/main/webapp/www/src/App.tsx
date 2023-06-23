import React, {useEffect, useState} from 'react';
import {createRoot} from "react-dom/client";
import ReactModal from "react-modal";
import {BrowserRouter, Route, useHistory} from "react-router-dom";
import Login from "./view/Login";
import Articles from "./view/Articles";
import ArticleData from "./view/ArticleData";

ReactModal.setAppElement('#root');

const App = () => {
    const [needUpdate, setNeedUpdate] = useState(new Date().getTime())
    const navigate = useHistory();
    const storageListener = (event?: any) => {
        console.log('changed')
        setNeedUpdate(new Date().getTime())
    }
    useEffect(() => {
        window.addEventListener('storage', storageListener, false)
        return () => window.removeEventListener('storage', storageListener)
    })
    return (
        <div>
            <BrowserRouter>
                <Route exact path={'/login*'}>
                    <Login/>
                </Route>
                <Route exact path={'/cp/articles*'}>
                    <Articles/>
                </Route>
                {['/cp/article/add', '/cp/article/o_:id'].map(value =>
                    <Route exact path={value}>
                        <ArticleData/>
                    </Route>
                )}
                {needUpdate > 0 && !!localStorage.getItem('user') &&
                    <div>
                        <a href={'/logout'} onClick={event => {
                            localStorage.removeItem('user')
                            window.dispatchEvent(new Event("storage"));
                        }}>Out
                        </a>
                    </div>
                }
            </BrowserRouter>
        </div>
    );
};

const container = document.getElementById('root') as HTMLElement;
const root = createRoot(container);
root.render(<App/>);
