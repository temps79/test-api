import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Article} from "../Types";

const Articles = () => {
    const [list, setList] = useState<Article[]>([])
    const role = Number(localStorage.getItem('role'))
    const loadList = () => {
        const user = JSON.parse(localStorage.getItem('user'))
        axios.get("/api/v1/article?page=1&size=10", {
            auth: {
                username: user.username,
                password: user.password,
            },
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(res => {
            console.log(res)
            setList(res.data)
        })
            .catch(alert)
    }


    useEffect(() => {
        loadList()
    }, [])
    return (
        <div>
            {role > 10 &&
                <div>
                    <a href={'/cp/article/add'}>Add new article</a>
                </div>
            }
            {list.map(value =>
                <div key={'list_article_' + value.id}>
                    <div>
                        Id:<a href={'/cp/article/o_' + value.id}>{value.id}</a>
                    </div>
                    <div>
                        Title:{value.title}
                    </div>
                    <div>
                        Author:{value.author}
                    </div>
                    {!!value.publisher &&
                        <div>
                            Publisher:{value.publisher.username}
                        </div>
                    }
                    <div>
                        Content:{Object.entries(value.content).map(value1 =>
                        <div>
                            {value1[0]}:{value1[1]}
                        </div>
                    )}
                    </div>
                    <div>
                        Publish date:{value.publishDate}
                    </div>
                </div>
            )}
        </div>
    );
};

export default Articles;