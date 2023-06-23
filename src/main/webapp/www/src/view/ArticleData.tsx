import React, {useEffect, useState} from 'react';
import axios from "axios";
import {useHistory, useParams} from "react-router-dom";
import {Article, UserDto} from "../Types";

type ArticleDataParams = {
    id: string
};
const ArticleData = () => {
        const params = useParams<ArticleDataParams>();
        const emptyContent = {
            key_1: ''
        } as any
        const emptyData = {
            author: '',
            content: emptyContent,
            title: '',
            id: -1
        } as Article
        const [data, setData] = useState<Article>(emptyData)
        const role = Number(localStorage.getItem('role'))
        const navigate = useHistory();
        const loadData = () => {
            const user = JSON.parse(localStorage.getItem('user'))
            axios.get("/api/v1/article/" + params.id, {
                auth: {
                    username: user.username,
                    password: user.password,
                },
                headers: {
                    'Content-Type': 'application/json',
                },
            }).then(res => {
                console.log(res)
                setData(res.data)
            })
                .catch(alert)
        }
        useEffect(() => {
            if (!!params.id && Number(params.id) > 0) {
                loadData()
            }
        }, [])
        const onChanged = (field: string) => (e: any) => {
            const newState = {...data}
            newState[field] = e.target.value
            setData(newState)
        }
        const onChangedContent = (ind: number) => (e: any) => {
            const newState = {...data}
            newState.content['key_' + ind] = e.target.value
            setData(newState)
        }
        const save = () => {
            const user = JSON.parse(localStorage.getItem('user'))
            const send = {...data}
            send.publisher = {username: user.username} as UserDto;
            send.publisherUsername = user.username;
            send.publishDate = new Date().getTime();
            console.log(JSON.stringify(send))
            axios.post("/api/v1/article", send, {
                auth: {
                    username: user.username,
                    password: user.password,
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8',
                },
            }).then(res => {
                if (!!params.id && Number(params.id) > 0) {
                    loadData()
                } else {
                    navigate.push('/cp/article/o_' + res.data)
                }
            }).catch(alert)
        }
        return (
            <div>
                <div>
                    <a href={'/cp/articles'}>back</a>
                </div>
                {!!data &&
                    <div>
                        {data.id > 0 &&
                            <div>
                                Id:<a href={'/cp/article/' + data.id}>{data.id}</a>
                            </div>
                        }
                        <div>
                            Title:{role > 10 ? <input value={data.title} onChange={onChanged('title')}/> : data.title}
                        </div>
                        <div>
                            Author:{role > 10 ? <input value={data.author} onChange={onChanged('author')}/> : data.author}
                        </div>
                        {!!data.publisher &&
                            <div>
                                Publisher:{data.publisher.username}
                            </div>
                        }
                        <div>
                            Content:{Object.entries(data.content).map((value1, ind) =>
                            <div>
                                {value1[0]}:{role > 10 ?
                                <input value={value1[1]} onChange={onChangedContent(ind + 1)}/> : value1[1]}
                            </div>
                        )}
                        </div>
                    </div>
                }
                <div>
                    {role > 10 &&
                        <button style={{margin: '5px'}} onClick={save}
                                disabled={data.title.length <= 0 || data.author.length <= 0}>
                            save
                        </button>
                    }
                </div>
            </div>
        )
            ;
    }
;

export default ArticleData;