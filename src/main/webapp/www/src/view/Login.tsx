import React, {useState} from 'react';
import {User} from "../Types";
import axios from "axios";
import {useHistory} from "react-router-dom";

const Login = () => {
    const emptyUser: User = {
        username: '',
        password: ''
    }
    const navigate = useHistory();
    const [user, setUser] = useState<User>(emptyUser)
    const onChanged = (field: string) => (e: any) => {
        const newState = {...user}
        newState[field] = e.target.value
        setUser(newState)
    }
    const checkLogin = () => {
        axios.post("api/v1/login", user, {
            auth: {
                username: user.username,
                password: user.password,
            },
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(res => {
            if (!!res.data) {
                localStorage.setItem('user', JSON.stringify(user))
                localStorage.setItem('role', res.data)
                window.dispatchEvent(new Event("storage"));
                navigate.push('/cp/articles')
            }
        })
            .catch(alert)
    }
    return (
        <div>
            Login
            <div>
                Username:<input onChange={onChanged('username')}/>
            </div>
            <div>
                Password:<input onChange={onChanged('password')} type={'password'}/>
            </div>
            <div>
                <button onClick={checkLogin}>login</button>
            </div>
        </div>
    );
};

export default Login;