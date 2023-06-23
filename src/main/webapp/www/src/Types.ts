export interface User {
    username: string,
    password: string

    [key: string]: any
}

export interface UserDto {
    username: string;
    roles?: string[];
}

export interface Article {
    id: number;
    title: string;
    author: string;
    content: any;
    publisherUsername?: string;
    publisher?: UserDto;
    publishDate?: number

    [key: string]: any
}