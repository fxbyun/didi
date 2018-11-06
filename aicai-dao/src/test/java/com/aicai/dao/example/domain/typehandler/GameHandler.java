package com.aicai.dao.example.domain.typehandler;

import org.apache.ibatis.type.MappedTypes;

import com.aicai.dao.example.domain.Game;
import com.aicai.dao.mybatis.typehandler.EnumDescriptionIdHandler;

@MappedTypes({Game.class})
public class GameHandler extends EnumDescriptionIdHandler{
}
